package com.publishers.api;

import com.publishers.helper.ErrorHelper;
import com.publishers.service.PublisherService;
import com.publishers.util.Constants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.stream.Collectors;

import static com.publishers.util.Constants.ApiAttribute.MESSAGE;


/**
 * Represents a controller that exposes endpoints for updating publisher's data.
 *
 * @author Ana Peterlic
 */
@Controller
public class PublisherController {

    private final PublisherService publisherService;

    /**
     * Creates an instance of publisher controller class.
     *
     * @param publisherService :: the publisher service object.
     */
    public PublisherController(PublisherService publisherService) {
        this.publisherService = publisherService;
    }

    @GetMapping("/")
    public String homepage(Model model) {
        return "index";
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file, RedirectAttributes attributes) {

        // check if file is empty
        if (file.isEmpty()) {
            attributes.addFlashAttribute(MESSAGE, Constants.ApiMessages.ERROR_FILE_NOT_SELECTED);
            return "redirect:/";
        }

        // process file
        try {
            publisherService.processFile(file);
        } catch (Exception e) {
            attributes.addFlashAttribute(MESSAGE, e.getMessage());
            ErrorHelper.clear();
            return "redirect:/";
        }

        // return success response
        attributes.addFlashAttribute(MESSAGE,
                ErrorHelper.get().isEmpty() ?
                        String.format(Constants.ApiMessages.OK_UPLOAD, file.getOriginalFilename()) :
                        ErrorHelper.get().stream().collect(Collectors.joining("\n", "", "\n")));
        ErrorHelper.clear();
        return "redirect:/";
    }
}
