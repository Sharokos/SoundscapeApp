package com.sharokos.soundscape.Controller;

import com.sharokos.soundscape.Model.CustomUser;
import com.sharokos.soundscape.Model.Sound;
import com.sharokos.soundscape.Model.Preset;
import com.sharokos.soundscape.Model.Soundscape;
import com.sharokos.soundscape.service.IPresetService;
import com.sharokos.soundscape.service.ISoundService;
import com.sharokos.soundscape.service.ISoundscapeService;
import com.sharokos.soundscape.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
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
            //Display every soundscape in the database
            List<Soundscape> soundScapes = soundscapeService.getAllSoundscapes();
            model.addAttribute("soundScapes", soundScapes);
        return "main-page";
    }
    @GetMapping("/soundscape/{soundscapeId}/{presetId}")
    public String showSoundscape(@PathVariable int soundscapeId, @PathVariable int presetId, Model model){
        // Get current username
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();


        Soundscape scape = soundscapeService.getSoundscapeById(soundscapeId);
        List<Sound> sounds = soundService.getSoundsBySoundscape(scape);
        List<Preset> userPresets = presetService.getPresetByUserAndSoundscape(username, soundscapeId);
        List<Preset> defaultPresets = presetService.getDefaultPresets(soundscapeId);
        Preset preset = presetService.getPresetById(presetId);
        preset.setAssociatedUsername(username);
        Map<String,Integer> freqMap =  preset.getSoundFrequency();

        for (String key : freqMap.keySet()) {
            System.out.println("Key: " + key);
        }
        for (Integer value : freqMap.values()) {
            System.out.println("Value: " + value);
        }
        List<Preset> allPresets = new ArrayList<Preset>();
        allPresets.addAll(userPresets);
        allPresets.addAll(defaultPresets);


        model.addAttribute("sounds", sounds);
        model.addAttribute("scape", scape);
        model.addAttribute("presetList", allPresets);
        model.addAttribute("preset", preset);

        return "soundscape-user";
    }

    @GetMapping("/showLoginPage")
    public String showLoginPage(){
        return "login-form";
    }
    @GetMapping("/registerUser")
    public String showRegistrationForm(Model model) {
        model.addAttribute("theUser", new CustomUser());
        return "register-form";
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
    public String savePreset(@ModelAttribute Preset thePreset){
        thePreset.setId(0);
        Preset savedPreset = presetService.savePreset(thePreset);
        int saveId = savedPreset.getId();
        int soundscapeId = savedPreset.getAssociatedSoundscape().getId();
        return "redirect:/soundscape/" + soundscapeId + "/" + saveId;
    }
}
