package homeTry.tag.teamTag.exception.badRequestException;

import homeTry.common.exception.BadRequestException;
import homeTry.tag.teamTag.exception.TeamTagErrorType;

public class TeamTagAlreadyExistsException extends BadRequestException {

    public TeamTagAlreadyExistsException() { super(TeamTagErrorType.TEAM_TAG_ALREADY_EXISTS_EXCEPTION); }
}
