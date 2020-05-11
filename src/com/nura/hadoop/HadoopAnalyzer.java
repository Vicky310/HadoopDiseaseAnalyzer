/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nura.hadoop;

import com.google.gson.Gson;
import com.nura.dao.impl.BestDoctorDAOImpl;
import com.nura.dao.impl.DoctorSelectedDAOImpl;
import com.nura.dao.impl.UserDetailsDAOImpl;
import com.nura.entity.BestDoctor;
import com.nura.entity.DoctorRating;
import com.nura.entity.DoctorSelected;
import com.nura.entity.PatientAppointment;
import com.nura.entity.UserDetails;
import com.nura.mail.SendMail;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;
import org.apache.hadoop.mapred.TextInputFormat;
import org.apache.hadoop.mapred.TextOutputFormat;

/**
 *
 * @author ArunRamya
 */
public class HadoopAnalyzer {

    public static class Map extends MapReduceBase implements Mapper<LongWritable, Text, Text, Text> {

        private final static IntWritable one = new IntWritable(1);
        private Text word = new Text();

        @Override
        public void map(LongWritable key, Text value, OutputCollector<Text, Text> output, Reporter reporter)
                throws IOException {
            String line = value.toString();
            PatientAppointment patApt = new Gson().fromJson(line, PatientAppointment.class);
            output.collect(new Text(patApt.getTokenId()), new Text(line));
        }
    }

    public static class Reduce extends MapReduceBase implements Reducer<Text, Text, Text, Text> {

        @Override
        public void reduce(Text key, Iterator<Text> values, OutputCollector<Text, Text> output,
                Reporter reporter) throws IOException {
            java.util.Map<String, List<String>> getDisDtls = com.nura.temp.store.StaticValueHolder.getVal();
            //System.out.println("Disease Details =>" + getDisDtls);
            java.util.Map<String, Integer> resultantMap = new java.util.HashMap<>();
            while (values.hasNext()) {
                String reccontent = values.next().toString();
                PatientAppointment _patApt = new Gson().fromJson(reccontent, PatientAppointment.class);
                System.out.println("Pat dis symp=>" + _patApt.getSymptoms());
                //System.out.println("Key =>" + key);
                //System.out.println("Rec content=>" + reccontent);
                int count = 0;
                for (java.util.Map.Entry<String, List<String>> entry : getDisDtls.entrySet()) {
                    for (String symp : _patApt.getSymptoms()) {
                        if (entry.getValue().contains(symp)) {
                            count++;
                        }
                    }
                    resultantMap.put(entry.getKey(), count);
                    count = 0;
                }
            }
            System.out.println("Resultant Map=>" + resultantMap);
            int maxCount = 0;
            String disName = "";
            for (java.util.Map.Entry<String, Integer> entryResult : resultantMap.entrySet()) {
                if (entryResult.getValue() > maxCount) {
                    disName = entryResult.getKey();
                    maxCount = entryResult.getValue();
                }
            }
            output.collect(key, new Text(disName + "\t" + maxCount));
        }
    }

    public void processFiles(File inputFile) throws Exception {

        HadoopAnalyzer hadoopAnalyzer = new HadoopAnalyzer();

        JobConf conf = new JobConf(HadoopAnalyzer.class);

        conf.set("fs.defaultFS", "hdfs://127.0.0.1:9000");
        conf.set("mapred.job.tracker", "127.0.0.1:9001");
        conf.setJobName("hadooptrans");

        conf.setOutputKeyClass(Text.class);
        conf.setOutputValueClass(Text.class);

        conf.setMapperClass(Map.class);
        //conf.setCombinerClass(Reduce.class);
        conf.setReducerClass(Reduce.class);

        conf.setInputFormat(TextInputFormat.class);
        conf.setOutputFormat(TextOutputFormat.class);

        //Code for accessing HDFS file system
        FileSystem hdfs = FileSystem.get(conf);
        Path homeDir = hdfs.getHomeDirectory();
        //Print the home directory
        System.out.println("Home folder -" + homeDir);

        //Add below code For creating and deleting directory
        Path workingDir = hdfs.getWorkingDirectory();
        Path newFolderPath = new Path("/input");
        newFolderPath = Path.mergePaths(workingDir, newFolderPath);
        if (hdfs.exists(newFolderPath)) {
            hdfs.delete(newFolderPath, true); //Delete existing Directory
        }

        hdfs.mkdirs(newFolderPath);     //Create new Directory

        //Code for copying File from local file system to HDFS
        String filePath = inputFile.getAbsolutePath();
        filePath = filePath.substring(0, filePath.lastIndexOf(File.separator));
        Path localFilePath = new Path(inputFile.getAbsolutePath());
        Path hdfsFilePath = new Path(newFolderPath + "/" + inputFile.getName());
        hdfs.copyFromLocalFile(localFilePath, hdfsFilePath);

        hdfs.copyFromLocalFile(localFilePath, newFolderPath);

        FileInputFormat.addInputPath(conf, hdfsFilePath);
        FileSystem fs = FileSystem.get(conf);
        Path out = new Path("hdfs://127.0.0.1:9000/hanout");
        boolean deleted = fs.delete(out, true);
        System.out.println("Hout delete status:-" + deleted);
        FileOutputFormat.setOutputPath(conf, new Path("hdfs://127.0.0.1:9000/hanout"));
        JobClient.runJob(conf);
        //Finally copying the out file to local after job has run
        fs.copyToLocalFile(new Path("hdfs://127.0.0.1:9000/hanout/part-00000"),
                new Path(constants.Constants.FILE_HADOOP_OUT_LOCATION));

        System.out.println("End of the program");
    }

    public static void main(String[] args) throws Exception {
        File inputFile = new File(constants.Constants.FILE_HADOOP_IN_LOCATION);
        FileWriter fileWriter = new FileWriter(inputFile);
        FileReader fileReader = new FileReader(new File(constants.Constants.FILE_HADOOP_OUT_LOCATION));
        Gson gson = new Gson();
        try {
            List<PatientAppointment> patList = new com.nura.dao.impl.PatientAppointmentDAOImpl().getPatientAppointment();
            for (PatientAppointment patApt : patList) {
                fileWriter.write(gson.toJson(patApt) + "\n");
            }
            fileWriter.flush();
            new HadoopAnalyzer().processFiles(inputFile);
            new com.nura.dao.impl.PatientAppointmentDAOImpl().updatePatientAppointment();
            BufferedReader buffReader = new BufferedReader(fileReader);
            String data;
            while ((data = buffReader.readLine()) != null) {
                System.out.println(data);
                String[] splitVal = data.split("\t");
                DoctorRating _bstDoc = new com.nura.dao.impl.DoctorRatingDAOImpl().getBstDocBsdOnDiseaseName(splitVal[1]);
                if (_bstDoc != null) {
                    UserDetails _usrDtls = new UserDetailsDAOImpl().getUserDtls(_bstDoc.getDocId());
                    DoctorSelected _docSel = new DoctorSelected();
                    _docSel.setDiesaeseType(splitVal[1]);
                    _docSel.setDocSelected(_usrDtls.getFirstName());
                    _docSel.setTokenId(splitVal[0]);
                    _docSel.setDocId(_usrDtls.getId());
                    _docSel.setMatchCount(Integer.parseInt(splitVal[2]));
                    _docSel.setStatus(constants.Constants.STATUS[0]);
                    new DoctorSelectedDAOImpl().savedDocSelDtls(_docSel);
                    //Send mail to doc selected
                    SendMail.main(_usrDtls.getUserName(), "Appointment is fixed for Token id "+splitVal[0]+"", "Appointment");
                } else {
                    UserDetails _usrDtls = new UserDetailsDAOImpl().getRandUserBsdOnDis(splitVal[1]);
                    DoctorSelected _docSel = new DoctorSelected();
                    _docSel.setDiesaeseType(splitVal[1]);
                    _docSel.setDocSelected(_usrDtls.getFirstName());
                    _docSel.setTokenId(splitVal[0]);
                    _docSel.setDocId(_usrDtls.getId());
                    _docSel.setMatchCount(Integer.parseInt(splitVal[2]));
                    _docSel.setStatus(constants.Constants.STATUS[0]);
                    new DoctorSelectedDAOImpl().savedDocSelDtls(_docSel);
                    //Send mail to doc selected
                    SendMail.main(_usrDtls.getUserName(), "Appointment is fixed for Token id "+splitVal[0]+"", "Appointment");
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            fileWriter.close();
        }
    }
}
