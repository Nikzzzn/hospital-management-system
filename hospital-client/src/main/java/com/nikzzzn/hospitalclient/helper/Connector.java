package com.nikzzzn.hospitalclient.helper;

import java.io.IOException;
import java.net.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.nikzzzn.hospitalclient.model.Appointment;
import com.nikzzzn.hospitalclient.model.Doctor;
import com.nikzzzn.hospitalclient.model.Patient;
import com.nikzzzn.hospitalclient.model.Specialty;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class Connector {

    private static final String urlString = "http://127.0.0.1:8080";
    private static final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    private static HttpURLConnection connection;

    public static Optional<List<Appointment>> getAppointments() throws IOException {
        List<Appointment> parsed = mapper.readValue(new URL(urlString + "/appointments"), new TypeReference<>() {});
        return Optional.of(parsed);
    }

    public static Optional<List<Appointment>> getAppointmentsForWeek() throws IOException {
        List<Appointment> parsed = mapper.readValue(new URL(urlString + "/weeks_schedule"), new TypeReference<>() {});
        return Optional.of(parsed);
    }

    public static Optional<List<Specialty>> getSpecialties() throws IOException {
        return Optional.of(mapper.readValue(new URL(urlString + "/specialties"), new TypeReference<>() {}));
    }

    public static Optional<List<Patient>> getPatients() throws IOException {
        return Optional.of(mapper.readValue(new URL(urlString + "/patients"), new TypeReference<>() {}));
    }

    public static Optional<List<Doctor>> getDoctors() throws IOException {
        return Optional.of(mapper.readValue(new URL(urlString + "/doctors"), new TypeReference<>() {}));
    }

    public static Optional<Doctor> getDoctorById(Integer id) throws IOException {
        StringBuilder sb = new StringBuilder(urlString);
        sb.append("/doctors_by_id")
          .append("?id=")
          .append(id);
        return Optional.of(mapper.readValue(new URL(sb.toString()), Doctor.class));
    }

    public static Optional<Map<Integer, List<LocalTime>>> getAvailableDoctors(LocalDate date, Integer specialty) throws IOException {
        StringBuilder sb = new StringBuilder(urlString);
        sb.append("/available_doctors")
          .append("?date=")
          .append(date)
          .append("&specialty=")
          .append(specialty);
        TypeReference<Hashtable<Integer, List<LocalTime>>> typeRef = new TypeReference<>() {};
        return Optional.of(mapper.readValue(new URL(sb.toString()), typeRef));
    }

    public static void saveAppointment(Appointment appointment) {
        try {
            CloseableHttpClient httpclient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(urlString + "/save_appointment");
            URI uri = new URIBuilder(httpPost.getURI())
                    .addParameter("id", appointment.id.toString())
                    .addParameter("doctorId", appointment.doctor.id.toString())
                    .addParameter("patientId", appointment.patient.id.toString())
                    .addParameter("date", appointment.date.toString())
                    .addParameter("time", appointment.time.toString())
                    .build();
            httpPost.setURI(uri);
            CloseableHttpResponse response = httpclient.execute(httpPost);
            httpclient.close();
        }
        catch (URISyntaxException | IOException e){
            e.printStackTrace();
        }
    }

    public static void deleteAppointment(Appointment appointment){
        try {
            CloseableHttpClient httpclient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(urlString + "/delete_appointment/" + appointment.id);
            URI uri = new URIBuilder(httpGet.getURI()).build();
            httpGet.setURI(uri);
            CloseableHttpResponse response = httpclient.execute(httpGet);
            httpclient.close();
        }
        catch (URISyntaxException | IOException e){
            e.printStackTrace();
        }
    }

    public static void saveDoctor(Doctor doctor) {
        try {
            CloseableHttpClient httpclient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(urlString + "/save_doctor");
            URI uri = new URIBuilder(httpPost.getURI())
                    .addParameter("id", doctor.id.toString())
                    .addParameter("name", doctor.name)
                    .addParameter("specialtyId", doctor.specialty.id.toString())
                    .build();
            httpPost.setURI(uri);
            CloseableHttpResponse response = httpclient.execute(httpPost);
            httpclient.close();
        }
        catch (URISyntaxException | IOException e){
            e.printStackTrace();
        }
    }

    public static void deleteDoctor(Doctor doctor) {
        try {
            CloseableHttpClient httpclient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(urlString + "/delete_doctor/" + doctor.id);
            URI uri = new URIBuilder(httpGet.getURI()).build();
            httpGet.setURI(uri);
            CloseableHttpResponse response = httpclient.execute(httpGet);
            httpclient.close();
        }
        catch (URISyntaxException | IOException e){
            e.printStackTrace();
        }
    }

    public static Optional<List<Doctor>> getDoctorsByName(String name) throws IOException {
        Optional<List<Doctor>> result = Optional.empty();
        try {
            CloseableHttpClient httpclient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(urlString + "/doctor_search");
            URI uri = new URIBuilder(httpPost.getURI())
                    .addParameter("name", name)
                    .build();
            httpPost.setURI(uri);
            CloseableHttpResponse response = httpclient.execute(httpPost);
            result = Optional.of(mapper.readValue(response.getEntity().getContent(), new TypeReference<>() {}));
            httpclient.close();
        }
        catch (URISyntaxException | IOException e){
            e.printStackTrace();
        }
        return result;
    }

    public static void savePatient(Patient patient) {
        try {
            CloseableHttpClient httpclient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(urlString + "/save_patient");
            URI uri = new URIBuilder(httpPost.getURI())
                    .addParameter("id", patient.id.toString())
                    .addParameter("name", patient.name)
                    .addParameter("dateOfBirth", patient.dateOfBirth.toString())
                    .addParameter("gender", patient.gender)
                    .addParameter("phone", patient.phone)
                    .addParameter("address", patient.address)
                    .build();
            httpPost.setURI(uri);
            CloseableHttpResponse response = httpclient.execute(httpPost);
            httpclient.close();
        }
        catch (URISyntaxException | IOException e){
            e.printStackTrace();
        }
    }

    public static void deletePatient(Patient patient) {
        try {
            CloseableHttpClient httpclient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(urlString + "/delete_patient/" + patient.id);
            URI uri = new URIBuilder(httpGet.getURI()).build();
            httpGet.setURI(uri);
            CloseableHttpResponse response = httpclient.execute(httpGet);
            httpclient.close();
        }
        catch (URISyntaxException | IOException e){
            e.printStackTrace();
        }
    }

    public static Optional<List<Patient>> getPatientsByName(String name) throws IOException {
        Optional<List<Patient>> result = Optional.empty();
        try {
            CloseableHttpClient httpclient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(urlString + "/patient_search");
            URI uri = new URIBuilder(httpPost.getURI())
                    .addParameter("name", name)
                    .build();
            httpPost.setURI(uri);
            CloseableHttpResponse response = httpclient.execute(httpPost);
            result = Optional.of(mapper.readValue(response.getEntity().getContent(), new TypeReference<>() {}));
            httpclient.close();
        }
        catch (URISyntaxException | IOException e){
            e.printStackTrace();
        }
        return result;
    }

    public static Optional<List<Appointment>> getAppointmentsForWeekByPatient(Patient patient) throws IOException {
        List<Appointment> parsed = mapper.readValue(
                new URL(urlString + "/appointments_week_patient/" + patient.id),
                new TypeReference<>() {});
        return Optional.of(parsed);
    }

    public static Optional<List<Appointment>> getAppointmentsByPatient(Patient patient) throws IOException {
        List<Appointment> parsed = mapper.readValue(
                new URL(urlString + "/appointments_patient/" + patient.id),
                new TypeReference<>() {});
        return Optional.of(parsed);
    }

    public static Optional<List<Appointment>> getAppointmentsForWeekByDoctor(Doctor doctor) throws IOException {
        List<Appointment> parsed = mapper.readValue(
                new URL(urlString + "/appointments_week_doctor/" + doctor.id),
                new TypeReference<>() {});
        return Optional.of(parsed);
    }

    public static Optional<List<Appointment>> getAppointmentsByDoctor(Doctor doctor) throws IOException {
        List<Appointment> parsed = mapper.readValue(
                new URL(urlString + "/appointments_doctor/" + doctor.id),
                new TypeReference<>() {});
        return Optional.of(parsed);
    }

    public static Optional<List<Doctor>> getDoctorsByPatient(Patient patient) throws IOException {
        List<Doctor> parsed = mapper.readValue(
                new URL(urlString + "/patient_doctors/" + patient.id),
                new TypeReference<>() {});
        return Optional.of(parsed);
    }

    public static Optional<List<Patient>> getPatientsByDoctor(Doctor doctor) throws IOException {
        List<Patient> parsed = mapper.readValue(
                new URL(urlString + "/doctor_patients/" + doctor.id),
                new TypeReference<>() {});
        return Optional.of(parsed);
    }
}
