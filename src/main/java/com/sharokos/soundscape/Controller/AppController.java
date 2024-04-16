package com.sharokos.soundscape.Controller;

import com.sharokos.soundscape.Model.CustomUser;
import com.sharokos.soundscape.Model.Sound;
import com.sharokos.soundscape.Model.Preset;
import com.sharokos.soundscape.Model.Soundscape;
import com.sharokos.soundscape.service.IPresetService;
import com.sharokos.soundscape.service.ISoundService;
import com.sharokos.soundscape.service.ISoundscapeService;
import com.sharokos.soundscape.service.IUserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;


import java.util.*;

@Controller
public class AppController {
    private ISoundscapeService soundscapeService;
    private IPresetService presetService;
    private IUserService userService;
    private ISoundService soundService;
    private int currentPresetId = 1 ;
    private int persistentSoundscapeId;

    @Autowired
    public AppController(ISoundscapeService soundscapeService, IPresetService presetService,
                         IUserService userService, ISoundService soundService){
        this.soundscapeService = soundscapeService;
        this.presetService = presetService;
        this.userService = userService;
        this.soundService = soundService;

    }
    @GetMapping("/main")
    public String showMainPage(Model model){
        int usedPresets = 0;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();



        if (Objects.equals(username, "anonymousUser")){
            username = "guest";
        }

        if (!username.equals("guest")) {
            CustomUser currentUser = (CustomUser) userService.getUserByName(username);
            usedPresets = currentUser.getNumberOfPresets();
        }
        //Display every soundscape in the database
        List<Soundscape> soundScapes = soundscapeService.getAllSoundscapes();
        model.addAttribute("soundScapes", soundScapes);
        model.addAttribute("username", username);
        model.addAttribute("usedPresets", usedPresets);

        return "main-page";
    }
    @PostMapping("/submit")
    public String handleSubmit(@RequestParam("hiddenField") String hiddenFieldValue) {
        // Use hiddenFieldValue in your backend logic
        currentPresetId = Integer.parseInt(hiddenFieldValue);
        Preset thePreset = presetService.getPresetById(currentPresetId);
        int soundscapeId = thePreset.getAssociatedSoundscape().getId();
        return "redirect:/soundscape/" + soundscapeId;
    }
    @GetMapping(value = {"/soundscape/{soundscapeIdStr}","/soundscape"})
    public String showSoundscape(@PathVariable(required = false) String soundscapeIdStr, Model model, HttpServletRequest request){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
        if (Objects.equals(username, "anonymousUser")){
            username = "guest";
        }
        int presetId = currentPresetId;
//        Preset tempPreset = presetService.getPresetById(presetId);
//        int tempScapeId = tempPreset.getAssociatedSoundscape().getId();
        // Get current username
        int soundscapeId;
        if (soundscapeIdStr == null){
            System.out.println("URL id is null");
            soundscapeId = persistentSoundscapeId;
        }
        else {

            soundscapeId = Integer.parseInt(soundscapeIdStr);
            persistentSoundscapeId = soundscapeId;
            System.out.println("URL id is not null. Saving persistent value of " + soundscapeId);
        }
        Soundscape scape = soundscapeService.getSoundscapeById(soundscapeId);
        List<Sound> sounds = soundService.getSoundsBySoundscape(scape);
        List<Preset> userPresetsOriginal = presetService.getPresetByUserAndSoundscape(username, soundscapeId);
        List<Preset> defaultPresetsOriginal = presetService.getDefaultPresets(soundscapeId);

        List<Preset> userPresets = new ArrayList<>();
        List<Preset> defaultPresets = new ArrayList<>();
        for (Preset element : userPresetsOriginal) {
            userPresets.add(element.getPresetDeepCopy());
        }
        for (Preset element : defaultPresetsOriginal) {
            defaultPresets.add(element.getPresetDeepCopy());
        }
        Preset preset = presetService.getPresetById(presetId);
        String presetName="";
        if (preset == null) {
            if(!defaultPresets.isEmpty()){
                preset = defaultPresetsOriginal.getFirst();
                preset.setAssociatedUsername(username);
                presetName = preset.getPresetName();
                preset.setPresetName("");
            }
        }
        else{
            preset.setAssociatedUsername(username);
            presetName = preset.getPresetName();
            preset.setPresetName("");
        }




        model.addAttribute("sounds", sounds);
        model.addAttribute("scape", scape);
        model.addAttribute("username", username);
        model.addAttribute("defaultPresetList", defaultPresets);
        model.addAttribute("userPresetList", userPresets);
        model.addAttribute("preset", preset);
        model.addAttribute("presetName", presetName);

        if (inputFlashMap != null) {
            String presetError = (String) inputFlashMap.get("presetError");
        }
        return "soundscape-user";

    }

    @GetMapping("/showLoginPage")
    public String showLoginPage(HttpServletRequest request){
        Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
        if (inputFlashMap != null) {
            String logoutMessage = (String) inputFlashMap.get("logoutMessage");
        }
        return "login-form";

    }
    @GetMapping("/registerUser")
    public String showRegistrationForm(Model model) {
        model.addAttribute("theUser", new CustomUser());
        return "register-form";
    }
    @GetMapping("/logout-temp")
    public String logoutSuccess(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("logoutMessage", "Logged out successful!");

        return "redirect:/showLoginPage";
    }
    @PostMapping("/register")
    public String registerSave(@ModelAttribute("theUser") @Valid CustomUser theUser, BindingResult result){
        //Register only if username does not already exist;
        if (userService.usernameExists(theUser.getUsername())) {
            result.rejectValue("username", "error.user", "User already exists!");
            return "register-form";
        }
        //Password confirmation;
        if (result.hasErrors() || !theUser.getPassword().equals(theUser.getConfirmPassword())) {
            System.out.println("Error when registering");
            result.rejectValue("confirmPassword", "error.user", "Password error!");
            return "register-form";
        }
        User savedUser = userService.saveUser(theUser);
        return "redirect:/main";
    }
    @PostMapping("/savePreset")
    public String savePreset(@ModelAttribute("preset") @Valid  Preset preset, BindingResult result, RedirectAttributes redirectAttributes,
                             @RequestParam("soundscapeId") int scapeId, Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        CustomUser currentUser = (CustomUser) userService.getUserByName(username);
        List<Preset> defaultPresets = presetService.getDefaultPresets(scapeId);
        int fallbackPresetId = defaultPresets.getFirst().getId();
        if (currentUser.getNumberOfPresets()<20){

            if (result.hasErrors()) {
                result.rejectValue("presetName", "error.user", "Incorrect name!");
                redirectAttributes.addFlashAttribute("presetError", "Invalid preset name!");
                return "redirect:/soundscape/" + scapeId;

            }
            currentUser.incrementPresets();
            preset.setId(0);
            Preset savedPreset = presetService.savePreset(preset);
            int saveId = savedPreset.getId();
            int soundscapeId = savedPreset.getAssociatedSoundscape().getId();
            // Process valid data
            return "redirect:/soundscape/" + scapeId;

        }
        else{
            redirectAttributes.addFlashAttribute("failedSave", "Maximum number of presets reached. Could not save!");

            return "redirect:/soundscape/" + scapeId;
        }
    }

    @GetMapping("/deletePreset")
    public String deletePreset(@RequestParam("presetId") int id, @RequestParam("soundscapeId") int scapeId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        System.out.println(username);
        CustomUser currentUser = (CustomUser) userService.getUserByName(username);

        currentUser.decrementPresets();

        presetService.deletePreset(id);
        List<Preset> defaultPresets = presetService.getDefaultPresets(scapeId);
        int fallbackPresetId = defaultPresets.getFirst().getId();
        return "redirect:/soundscape/" + scapeId;
    }
}
