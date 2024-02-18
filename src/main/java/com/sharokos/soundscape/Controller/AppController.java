package com.sharokos.soundscape.Controller;

import com.sharokos.soundscape.Model.Sound;
import com.sharokos.soundscape.Model.Preset;
import com.sharokos.soundscape.Model.Soundscape;
import com.sharokos.soundscape.service.ISoundscapeService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class AppController {
    private ISoundscapeService soundscapeService;

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
        Soundscape scape = soundscapeService.getSoundscapeById(soundscapeId);
        List<Sound> sounds = soundscapeService.getSoundsBySoundscape(scape);
        List<Preset> userPresets = soundscapeService.getPresetByUserAndSoundscape(username, soundscapeId);
        List<Preset> defaultPresets = soundscapeService.getDefaultPresets(soundscapeId);
        Preset preset = soundscapeService.getPresetById(presetId);
        List<Preset> allPresets = new ArrayList<Preset>();
        allPresets.addAll(userPresets);
        allPresets.addAll(defaultPresets);
        model.addAttribute("sounds", sounds);
        model.addAttribute("scape", scape);
        model.addAttribute("presets", allPresets);
        model.addAttribute("preset", preset);
        return "soundscape-user";
    }

    @GetMapping("/showLoginPage")
    public String showLoginPage(){
        return "login-form";
    }

    @PostMapping("/savePreset")
    public String savePreset(@ModelAttribute Preset thePreset){
        thePreset.setId(0);
        Preset savedPreset = soundscapeService.savePreset(thePreset);
        // you can also retrieve the soundScape Id of the preset after you implement this. Get the Id and use it in the URL to navigate back
        int saveId = savedPreset.getId();
        return "redirect:/soundscape/1/" +saveId;
    }
}
