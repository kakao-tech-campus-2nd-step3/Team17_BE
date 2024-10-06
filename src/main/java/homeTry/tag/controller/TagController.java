package homeTry.tag.controller;

import homeTry.tag.service.TagService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//추후 관리자 쪽에서 api 추가 예정

@RestController
@RequestMapping("/api/admin/tag")
public class TagController {
    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }
}
