package homeTry.tag.teamTag.exception.badRequestException;

import homeTry.tag.teamTag.exception.TeamTagErrorType;
import homeTry.common.exception.BadRequestException;

public class TeamTagNotFoundException extends BadRequestException {
    
    public TeamTagNotFoundException() {
        super(TeamTagErrorType.TEAM_TAG_NOT_FOUND_EXCEPTION);
    }
    
}
