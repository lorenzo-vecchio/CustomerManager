package com.CustomerManager.demo.setup;

import com.CustomerManager.demo.models.users.Admin;
import com.CustomerManager.demo.repositories.AdminRepository;
import com.CustomerManager.demo.services.security.InfisicalService;
import com.CustomerManager.demo.services.system.SystemInfoProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



@Component
public class Setup {

    Logger logger = LoggerFactory.getLogger(Setup.class);

    @Autowired
    SystemInfoProperties systemInfoProperties;

    @Autowired
    InfisicalService infisicalService;

    int initialSetupCount = 0;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    AdminRepository adminRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void initialSetup() {
        logger.info(createFormattedMessage());
        logger.info(createFormattedMessage());
        logger.info(createFormattedMessage());
        logger.info(createFormattedMessage("CHECKING INITIAL SETUP"));
        checkInitialSetup();
        logger.info(createFormattedMessage("SETUP FINISHED - STATUS = OK"));
        logger.info(createFormattedMessage());
        logger.info(createFormattedMessage());
        logger.info(createFormattedMessage("WELCOME"));
        logger.info(createFormattedMessage());
        logger.info(createFormattedMessage());
        logger.info(createFormattedMessage("APPLICATION STARTED AND READY TO TAKE REQUESTS"));
        logger.info(createFormattedMessage());
        logger.info(createFormattedMessage());
        logger.info(createFormattedMessage());
    }

    public void checkInitialSetup() throws IllegalStateException {
        initialSetupCount++;
        logger.info(createFormattedMessage("checking admin existence..."));
        boolean adminCreated = systemInfoProperties.getBooleanProperty("admin_created");
        boolean adminExistsInDB = (adminRepository.count() > 0);
        if (!adminCreated || !adminExistsInDB) {
            logger.info(createFormattedMessage("creating admin..."));
            loadAndCreateAdmin();
            logger.info(createFormattedMessage("admin created"));
            if (initialSetupCount > 10) {
                throw new IllegalStateException("Initial setup check exceeded maximum attempts.");
            } else {
                checkInitialSetup();
            }
        } else {
            logger.info(createFormattedMessage("admin exists"));
        }
    }


    public void loadAndCreateAdmin() {
        String email = infisicalService.getAdminEmail();
        String password = infisicalService.getAdminPassword();
        Admin admin = new Admin();
        admin.setEmail(email);
        admin.setPassword(passwordEncoder.encode(password));
        admin.setFirstName("Admin");
        admin.setLastName("Admin");
        adminRepository.save(admin);
        systemInfoProperties.setBooleanProperty("admin_created", true);
    }

    public String createFormattedMessage(String message, int length) {
        if (message.length() >= length) {
            return message;
        }
        int totalPadding = length - message.length();
        int paddingOnEachSide = totalPadding / 2;
        int leftPadding = paddingOnEachSide;
        int rightPadding = paddingOnEachSide;
        if (totalPadding % 2 != 0) {
            rightPadding++;
        }
        StringBuilder formattedMessage = new StringBuilder();
        for (int i = 0; i < leftPadding; i++) {
            formattedMessage.append('-');
        }
        formattedMessage.append(message);
        for (int i = 0; i < rightPadding; i++) {
            formattedMessage.append('-');
        }
        return formattedMessage.toString();
    }


    public String createFormattedMessage (String message) {
        return createFormattedMessage(message, 80);
    }

    public String createFormattedMessage() {
        return createFormattedMessage("");
    }


}
