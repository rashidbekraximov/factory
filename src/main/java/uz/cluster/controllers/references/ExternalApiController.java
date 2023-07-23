package uz.cluster.controllers.references;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@Api(value = "External Api Controller")
@Tag(name = "Ob-havo va Valyuta kurslari")
@RequestMapping("/")
public class ExternalApiController {

    private final RestTemplate restTemplate;

    public ExternalApiController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @ApiOperation(value = "get weather api", response = Long.class)
    @GetMapping(value = "/weather")
    public Object getWeather() {
        return restTemplate.getForObject("http://api.weatherapi.com/v1/current.json?key=9fbeb2684c214208ae0142433221403&q=Urgench&aqi=no", Object.class);
    }

    @ApiOperation(value = "get currency api", response = Long.class)
    @GetMapping(value = "/currency")
    public Object getCurrency() {
        return restTemplate.getForObject("https://cbu.uz/uz/arkhiv-kursov-valyut/json", Object.class);
    }
}

