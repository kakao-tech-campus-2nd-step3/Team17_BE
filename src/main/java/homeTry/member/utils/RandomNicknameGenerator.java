package homeTry.member.utils;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public final class RandomNicknameGenerator {

    private static final String[] ADJECTIVES = {
            "멋진", "행복한", "용감한", "차가운", "뜨거운",
            "귀여운", "화려한", "시원한", "아기자기한", "달콤한",
            "신비한", "고요한", "따뜻한", "기운찬", "강력한",
            "빛나는", "순수한", "든든한", "똑똑한", "즐거운",
            "상냥한", "자유로운", "활기찬", "친절한", "단단한",
            "다정한", "사랑스러운", "감성적인", "우아한", "매력적인",
            "재미있는", "호기심많은", "근사한", "솔직한", "열정적인"
    };

    private static final String[] NOUNS = {
            "고양이", "호랑이", "사자", "늑대", "여우",
            "곰", "토끼", "다람쥐", "햄스터", "펭귄",
            "원숭이", "도마뱀", "독수리", "부엉이", "용",
            "돌고래", "코끼리", "사슴", "나무",
            "별", "달", "태양", "꽃", "바다",
            "구름", "산", "강아지", "나비", "오리",
            "물고기", "벌새", "수달", "청설모", "사과",
            "장미", "연꽃", "바위", "은하수",
            "별빛", "푸른하늘", "폭포", "초원", "숲속",
            "단비", "바람", "천둥", "번개", "불꽃",
            "수평선", "해변", "파도", "조개", "산호",
            "석양", "모래사장", "계곡", "노을", "무지개",
            "유성", "꽃잎", "눈송이", "잎사귀", "돌맹이",
            "분수", "안개", "별자리", "산들바람", "오솔길",
            "바람결", "잉어", "달팽이", "단풍", "코스모스",
            "들꽃", "개미", "벌", "매미", "호수",
            "운하", "기린", "풍뎅이", "이슬", "물안개",
            "늪지", "초록잎", "물방울", "달빛", "자작나무",
            "대나무", "갈대", "소나무", "장수풍뎅이", "개구리",
            "도토리", "고사리", "솔잎", "수정", "산호초",
            "별무리", "파란새", "햇살", "소금꽃", "바위산"
    };

    public static String generateNickname() {
        Random random = ThreadLocalRandom.current();
        String adjective = ADJECTIVES[random.nextInt(ADJECTIVES.length)];
        String noun = NOUNS[random.nextInt(NOUNS.length)];
        return adjective + noun;
    }

}
