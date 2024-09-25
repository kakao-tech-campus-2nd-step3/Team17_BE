package homeTry.mainPage.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import homeTry.mainPage.dto.request.MainPageRequest;
import homeTry.mainPage.dto.response.MainPageResponse;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/api")
public class MainPageController {
    
    @GetMapping
    public ResponseEntity<MainPageResponse> mainPage(
        @RequestHeader("Authorization") String authorizationHeader,
        @RequestBody MainPageRequest mainPageRequest) {
        
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
