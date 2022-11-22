package eridanus.sponsio.helper;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Bookie {

    BETANO("Betano"),
    MOZZART("Mozzart");

    private final String bookieName;

}
