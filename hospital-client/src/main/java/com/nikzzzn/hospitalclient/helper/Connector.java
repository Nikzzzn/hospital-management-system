package com.nikzzzn.hospitalclient.helper;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;
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
import com.nikzzzn.hospitalclient.holder.AppointmentHolder;
import com.nikzzzn.hospitalclient.model.Appointment;
import com.nikzzzn.hospitalclient.model.Doctor;
import com.nikzzzn.hospitalclient.model.Patient;
import com.nikzzzn.hospitalclient.model.Specialty;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;

public class Connector {

    private static final String urlString = "http://127.0.0.1:8080";
    private static final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    private static HttpURLConnection connection;

    public static Optional<List<AppointmentHolder>> getAppointments() throws IOException {
        List<Appointment> parsed = mapper.readValue(new URL(urlString), new TypeReference<>() {});
        return Optional.of(Converter.convertToHolder(parsed));
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

    public static void saveAppointment(Integer doctorId, Integer patientId, LocalDate date, LocalTime time) {
        try {
            CloseableHttpClient httpclient = HttpClients.createDefault();
            HttpPost httpGet = new HttpPost(urlString + "/save_appointment");
            URI uri = new URIBuilder(httpGet.getURI())
                    .addParameter("doctorId", doctorId.toString())
                    .addParameter("patientId", patientId.toString())
                    .addParameter("date", date.toString())
                    .addParameter("time", time.toString())
                    .build();
            httpGet.setURI(uri);
            CloseableHttpResponse response = httpclient.execute(httpGet);
            httpclient.close();
        }
        catch (URISyntaxException | IOException e){
            e.printStackTrace();
        }
    }

}
