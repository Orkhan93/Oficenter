package az.digitalhands.oficenter.wrapper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AboutWrapper {
    private Long id;
    private String title;
    private String content;
    private String imageOfData;
}
