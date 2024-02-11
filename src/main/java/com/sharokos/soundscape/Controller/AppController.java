package com.sharokos.soundscape.Controller;

import com.sharokos.soundscape.Model.Sound;
import com.sharokos.soundscape.Model.Soundscape;
import com.sharokos.soundscape.service.ISoundscapeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class AppController {
    private ISoundscapeService soundscapeService;

    public AppController(ISoundscapeService soundscapeService){
        this.soundscapeService = soundscapeService;
    }
    @GetMapping("/main")
    public String showMainPage(Model model){
            Soundscape scape = soundscapeService.getSoundscapeById(1);
            System.out.println(scape.getSoundscapeName());
            List<Sound> sounds = soundscapeService.getSoundsBySoundscape(scape);
            List<Soundscape> soundScapes = soundscapeService.getAllSoundscapes();
            //System.out.println(sounds);
            model.addAttribute("soundScapes", soundScapes);
            model.addAttribute("sounds", sounds);



        return "main-page";
    }
}
