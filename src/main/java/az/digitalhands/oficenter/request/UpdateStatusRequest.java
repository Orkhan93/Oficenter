package az.digitalhands.oficenter.request;

import az.digitalhands.oficenter.enums.StatusRole;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateStatusRequest {
    Long id;
    StatusRole currentRole;
    StatusRole newRole;
}
