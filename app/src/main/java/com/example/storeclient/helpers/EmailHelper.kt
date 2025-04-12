package com.example.storeclient.helpers

import android.content.Context
import android.util.Log
import com.example.storeclient.config.AppConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.util.Properties
import javax.activation.DataHandler
import javax.activation.FileDataSource
import javax.mail.Authenticator
import javax.mail.Message
import javax.mail.PasswordAuthentication
import javax.mail.Session
import javax.mail.Transport
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeBodyPart
import javax.mail.internet.MimeMessage
import javax.mail.internet.MimeMultipart

object EmailHelper {

    suspend fun sendInventoryEmail(context: Context, file: File, body: String) {
        withContext(Dispatchers.IO) {
            try {
                val props = Properties().apply {
                    put("mail.smtp.auth", "true")
                    put("mail.smtp.starttls.enable", "true")
                    put("mail.smtp.host", AppConfig.SMTP_HOST)
                    put("mail.smtp.port", AppConfig.SMTP_PORT)
                }

                val session = Session.getInstance(props, object : Authenticator() {
                    override fun getPasswordAuthentication(): PasswordAuthentication {
                        return PasswordAuthentication(AppConfig.EMAIL_USER, AppConfig.EMAIL_PASSWORD)
                    }
                })

                val message = MimeMessage(session).apply {
                    setFrom(InternetAddress(AppConfig.EMAIL_USER))
                    setRecipients(Message.RecipientType.TO, InternetAddress.parse(AppConfig.RECEIVER_EMAIL))
                    subject = "Inventario Actual"

                    val multipart = MimeMultipart()

                    val textPart = MimeBodyPart()
                    textPart.setText(body)
                    multipart.addBodyPart(textPart)

                    val attachmentPart = MimeBodyPart()
                    val source = FileDataSource(file)
                    attachmentPart.dataHandler = DataHandler(source)
                    attachmentPart.fileName = file.name
                    multipart.addBodyPart(attachmentPart)

                    setContent(multipart)
                }

                Transport.send(message)
                Log.d("EmailHelper", "Correo enviado correctamente")
            } catch (e: Exception) {
                Log.e("EmailHelper", "Error al enviar correo", e)
            }
        }
    }
}
