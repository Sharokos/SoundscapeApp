package com.sharokos.soundscape.Controller;

import com.sharokos.soundscape.Model.Sound;
import com.sharokos.soundscape.Model.Preset;
import com.sharokos.soundscape.Model.Soundscape;
import com.sharokos.soundscape.service.ISoundscapeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        Soundscape scape = soundscapeService.getSoundscapeById(soundscapeId);
        List<Sound> sounds = soundscapeService.getSoundsBySoundscape(scape);
        Preset preset = soundscapeService.getPresetById(presetId);
        System.out.println(preset.getPresetName());
        Map<String, Integer> testiMap = preset.getSoundVolumes();
        for (Map.Entry<String, Integer> entry : testiMap.entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();

            // Perform your operations here
            System.out.println("Key: " + key + ", Value: " + value);
        }

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
    public String savePreset(@ModelAttribute Preset thePreset){
        thePreset.setId(0);
        Preset savedPreset = soundscapeService.savePreset(thePreset);
        // you can also retrieve the soundScape Id of the preset after you implement this. Get the Id and use it in the URL to navigate back
        int saveId = savedPreset.getId();
        return "redirect:/soundscape/1/" +saveId;
    }
}
