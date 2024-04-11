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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.validation.Valid;

import java.util.*;

@Controller
public class AppController {
    private ISoundscapeService soundscapeService;
    private IPresetService presetService;
    private IUserService userService;
    private ISoundService soundService;

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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        if (Objects.equals(username, "anonymousUser")){
            username = "guest";
        }
        //Display every soundscape in the database
        List<Soundscape> soundScapes = soundscapeService.getAllSoundscapes();
        model.addAttribute("soundScapes", soundScapes);
        model.addAttribute("username", username);

        return "main-page";
    }
    @GetMapping("/soundscape/{soundscapeId}/{presetId}")
    public String showSoundscape(@PathVariable int soundscapeId, @PathVariable int presetId, Model model){
        // Get current username
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        if (Objects.equals(username, "anonymousUser")){
            username = "guest";
        }

        Soundscape scape = soundscapeService.getSoundscapeById(soundscapeId);
        List<Sound> sounds = soundService.getSoundsBySoundscape(scape);
        List<Preset> userPresets = presetService.getPresetByUserAndSoundscape(username, soundscapeId);
        List<Preset> defaultPresets = presetService.getDefaultPresets(soundscapeId);
        Preset preset = presetService.getPresetById(presetId);
        if (preset == null) {
            if(!defaultPresets.isEmpty()){
                preset = defaultPresets.getFirst();
                preset.setAssociatedUsername(username);

            }
        }
        else{
            preset.setAssociatedUsername(username);

        }


        model.addAttribute("sounds", sounds);
        model.addAttribute("scape", scape);
        model.addAttribute("username", username);
        model.addAttribute("defaultPresetList", defaultPresets);
        model.addAttribute("userPresetList", userPresets);
        model.addAttribute("preset", preset);


        return "soundscape-user";
    }

    @GetMapping("/showLoginPage")
    public String showLoginPage(HttpServletRequest request){
        Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
        if (inputFlashMap != null) {
            String logoutMessage = (String) inputFlashMap.get("logoutMessage");
            return "login-form";
        } else {
            return "login-form";
        }

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
            result.rejectValue("confirmPassword", "error.user", "Passwords do not match!");
            return "register-form";
        }
        User savedUser = userService.saveUser(theUser);
        return "redirect:/main";
    }
    @PostMapping("/savePreset")
    public String savePreset(@ModelAttribute Preset thePreset, RedirectAttributes redirectAttributes,
                             @RequestParam("soundscapeId") int scapeId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        CustomUser currentUser = (CustomUser) userService.getUserByName(username);
        if (currentUser.getNumberOfPresets()<7){
            currentUser.incrementPresets();
            thePreset.setId(0);
            Preset savedPreset = presetService.savePreset(thePreset);
            int saveId = savedPreset.getId();
            int soundscapeId = savedPreset.getAssociatedSoundscape().getId();
            return "redirect:/soundscape/" + soundscapeId + "/" + saveId;
        }
        else{
            redirectAttributes.addFlashAttribute("failedSave", "Maximum number of presets reached. Could not save!");
            List<Preset> defaultPresets = presetService.getDefaultPresets(scapeId);
            int fallbackPresetId = defaultPresets.getFirst().getId();
            return "redirect:/soundscape/" + scapeId + "/" + fallbackPresetId;
        }
    }

    @GetMapping("/deletePreset")
    public String deletePreset(@RequestParam("presetId") int id, @RequestParam("soundscapeId") int scapeId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        CustomUser currentUser = (CustomUser) userService.getUserByName(username);

        currentUser.decrementPresets();

        presetService.deletePreset(id);
        List<Preset> defaultPresets = presetService.getDefaultPresets(scapeId);
        int fallbackPresetId = defaultPresets.getFirst().getId();
        return "redirect:/soundscape/" + scapeId + "/" + fallbackPresetId;
    }
}
