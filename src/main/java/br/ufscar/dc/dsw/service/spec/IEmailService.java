package br.ufscar.dc.dsw.service.spec;

public interface IEmailService {
    void sendEmail(String to, String subject, String body);
}