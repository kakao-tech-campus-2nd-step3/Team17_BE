package homeTry.common.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.time.Duration;

// Duration -> Long(milliseconds)로 변환하여 DB 저장
@Converter(autoApply = true)
public class DurationToLongConverter implements AttributeConverter<Duration, Long> {

    @Override
    public Long convertToDatabaseColumn(Duration duration) {
        return (duration != null) ? duration.toMillis() : null;
    }

    @Override
    public Duration convertToEntityAttribute(Long milliseconds) {
        return (milliseconds != null) ? Duration.ofMillis(milliseconds) : null;
    }

}
