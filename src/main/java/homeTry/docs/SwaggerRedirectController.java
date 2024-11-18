package homeTry.docs;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/docs")
public class SwaggerRedirectController {
    @GetMapping
    public String redirectToSwagger() { return "redirect:/swagger-ui/index.html?urls.primaryName=User+Services"; }
}
