package pl.mdj.rejestrbiurowy.clientaccess.rest;


import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.mdj.rejestrbiurowy.service.interfaces.CommentService;

@AllArgsConstructor
@RestController
@RequestMapping("comment")
public class CommentController {
    /*
    * Comment
     */

    CommentService commentService;
}
