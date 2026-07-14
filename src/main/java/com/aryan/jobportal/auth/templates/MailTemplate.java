package com.aryan.jobportal.auth.templates;

public class MailTemplate {

    public static String sendOtpTemplate(String otp) {
        return """
                <html>
                    <body style="font-family:Arial">
                        <h2>Industrial Job Portal</h2>

                        <p>Your OTP is</p>

                        <h1>%s</h1>

                        <p>This OTP expires in 10 minutes.</p>
                    </body>
                </html>
                """.formatted(otp);
    }


}
