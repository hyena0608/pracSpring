package prac.manboki.web.pedometer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import prac.manboki.domain.pedometer.PedometerService;

@Controller
@Slf4j
@RequiredArgsConstructor
public class PedometerController {

    private final PedometerService pedometerService;

    @PostMapping("/update/pedometer")
    public String updatePedomemter(@PathVariable int todaySteps, @PathVariable String email) {

        pedometerService.updatePedomemter(email, todaySteps);

        return "redirect:/v1/pedometer";
    }


}
