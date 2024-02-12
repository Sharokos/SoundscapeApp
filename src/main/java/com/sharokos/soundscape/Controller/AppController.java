package com.sharokos.soundscape.Controller;

import com.sharokos.soundscape.Model.Sound;
import com.sharokos.soundscape.Model.Preset;
import com.sharokos.soundscape.Model.Soundscape;
import com.sharokos.soundscape.service.ISoundscapeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

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
        Soundscape scape = soundscapeService.getSoundscapeById(soundscapeId);
        List<Sound> sounds = soundscapeService.getSoundsBySoundscape(scape);
        Preset preset = soundscapeService.getPresetById(presetId)
        model.addAttribute("sounds", sounds);
        model.addAttribute("scape", scape);
        model.addAttribute("preset", preset);
        return "soundscape-user";
    }

    @GetMapping("/showLoginPage")
    public String showLoginPage(){
        return "login-form";
    }

    @PostMapping("/savePreset")
    public void savePreset(@RequestBody Preset thePreset){
        System.out.println(thePreset.getSoundVolumes());
    }
}
