package com.nikzzzn.hospitalclient.helper;

import java.io.IOException;
import java.net.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
        List<Appointment> parsed = mapper.readValue(new URL(urlString), new TypeReference<>() {});
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

}
