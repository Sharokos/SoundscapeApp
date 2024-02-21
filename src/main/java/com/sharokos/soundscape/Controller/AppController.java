package com.sharokos.soundscape.Controller;

import com.sharokos.soundscape.Model.CustomUser;
import com.sharokos.soundscape.Model.Sound;
import com.sharokos.soundscape.Model.Preset;
import com.sharokos.soundscape.Model.Soundscape;
import com.sharokos.soundscape.service.ISoundscapeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
public class AppController {
    private ISoundscapeService soundscapeService;

    @Autowired
    public AppController(ISoundscapeService soundscapeService){
        this.soundscapeService = soundscapeService;

    }
    @GetMapping("/main")
    public String showMainPage(Model model){


            List<Soundscape> soundScapes = soundscapeService.getAllSoundscapes();

            model.addAttribute("soundScapes", soundScapes);




        return "main-page";
    }
    @GetMapping("/soundscape/{soundscapeId}/{presetId}")
    public String showSoundscape(@PathVariable int soundscapeId, @PathVariable int presetId, Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Get the username
        String username = authentication.getName();
        System.out.println("Logged in user is: " + username);
        Soundscape scape = soundscapeService.getSoundscapeById(soundscapeId);
        List<Sound> sounds = soundscapeService.getSoundsBySoundscape(scape);
        List<Preset> userPresets = soundscapeService.getPresetByUserAndSoundscape(username, soundscapeId);
        List<Preset> defaultPresets = soundscapeService.getDefaultPresets(soundscapeId);
        Preset preset = soundscapeService.getPresetById(presetId);
        preset.setAssociatedUsername(username);
        List<Preset> allPresets = new ArrayList<Preset>();
        allPresets.addAll(userPresets);
        allPresets.addAll(defaultPresets);

        for (Preset item : allPresets) {
            System.out.println(item.getPresetName());
        }
        System.out.println("(END) Logged in user is: " + username);
        model.addAttribute("username", username);
        model.addAttribute("sounds", sounds);
        model.addAttribute("scape", scape);
        model.addAttribute("presetList", allPresets);
        model.addAttribute("preset", preset);

        System.out.println("(END) Logged in user is: " + username);
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
    public String registerSave(@ModelAttribute CustomUser theUser){

        System.out.println("Registered!" + theUser.getUsername());
        User savedUser = soundscapeService.saveUser(theUser);
        return "redirect:/main";
    }
    @PostMapping("/savePreset")
    public String savePreset(@ModelAttribute Preset thePreset){
        thePreset.setId(0);
        Preset savedPreset = soundscapeService.savePreset(thePreset);
        System.out.println("Preset with username: " + thePreset.getAssociatedUsername());
        // you can also retrieve the soundScape Id of the preset after you implement this. Get the Id and use it in the URL to navigate back
        int saveId = savedPreset.getId();
        return "redirect:/soundscape/1/" +saveId;
    }
}
