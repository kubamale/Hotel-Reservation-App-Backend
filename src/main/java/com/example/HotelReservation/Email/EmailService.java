package com.example.HotelReservation.Email;

import com.example.HotelReservation.Models.Hotel;
import com.example.HotelReservation.Models.Reservation;
import com.example.HotelReservation.Models.Room;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Service
public class EmailService{
    private final JavaMailSender mailSender;
    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;

    }


    @Async
    public void notifyAboutCancellingReservation(Reservation reservation){

            String subject = "Canceling reservation: " + reservation.reservationNumber;
            String content = HTMLCancellingReservationEmailTemplate(reservation);
            sendSimpleEmail(reservation, subject, content);

    }

    @Async
    public void notifyAboutPlacingReservation(Reservation reservation){

            String subject = "Your reservation is complete!";
            String content = HTMLPlacingReservationEmailTemplate(reservation);
            sendSimpleEmail(reservation, subject, content);

    }



    private void sendSimpleEmail(Reservation reservations, String subject, String content) {

            MimeMessage message = mailSender.createMimeMessage();
            try {
                message.setRecipients(Message.RecipientType.TO, reservations.email);
                message.setSubject(subject);
                message.setContent(content, "text/html; charset=utf-8");
                mailSender.send(message);
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
        System.out.println("mail wysłany");

    }

    private String HTMLPlacingReservationEmailTemplate(Reservation reservation){
        Hotel hotel = reservation.getRoom().getHotel();
        Room room = reservation.getRoom();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String startDate = dateFormat.format(reservation.startDate);
        String endDate = dateFormat.format(reservation.endDate);
        long diffInMillies = Math.abs(reservation.endDate.getTime() - reservation.startDate.getTime());
        long dayPassed = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);

        return "<h1>Hello " + reservation.firstName +"</h1>" +
                "<p>We are very pleased that you chose to stay in our hotel!</p>" +
                "<p>Here are the details:</p>" +
                "<ul>" +
                    "<li>Reservation number: "+ reservation.reservationNumber +"</li>"+
                    "<li>Hotel: "+ hotel.name +"</li>"+
                    "<li>Address: "+ hotel.country +", " + hotel.city + ", " + hotel.street+ ", " + hotel.streetNumber +"</li>" +
                    "<li>Duration: From " + startDate + " to "+ endDate +"</li>" +
                    "<li>Max. guest amount: "+ room.capacity+"</li>" +
                    "<li>Price: "+ dayPassed*room.price +"</li>" +
                "</ul>" +
                "</br>" +
                "<p>Pleae for more information contact your hotel at " + hotel.phoneNumber + "or by email at " + hotel.email + ".</p>";

    }

    private String HTMLCancellingReservationEmailTemplate(Reservation reservation){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String startDate = dateFormat.format(reservation.startDate);
        String endDate = dateFormat.format(reservation.endDate);
        return "<h1>Hello " + reservation.firstName +"</h1>" +
                "<p>Your reservation for "+ reservation.getRoom().getHotel().name + " from " + startDate+ " to " + endDate +" was canceled</p>" +
                "<p>We hope that you will choose to stay with us in the future</p>" +
                "<h3>With regards</h3>" +
                "<p>" +reservation.getRoom().getHotel().name + " team.</p>";
    }

}
