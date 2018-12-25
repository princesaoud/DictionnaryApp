package com.example.princesaoud.dictionnaryapp;

public class DB {

    public static String[] getData(int id){
        if(id == R.id.item_english_french){
            return getEngFrench();
        }else if(id == R.id.item_french_english){
            return getFrenchEng();
        }
        return new String[0];
    }

    public static String[] getFrenchEng(){

        String[] source = new String[]{
                "anéchoïque",
                "anéchoïsme",
                "anécique",
                "ânée",
                "anéjaculation",
                "anel",
                "anélasticité",
                "anélastique",
                "anémiant",
                "anémié",
                "anémier",
                "anémique",
                "anémo",
                "anémochore",
                "anémochorie",
                "anémocorde",
                "anémogame",
                "anémogamie",
                "anémographe",
                "anémographie",
                "anémomètre",
                "anémométrie",
                "anémométrique",
                "anémométroscope",
                "anémone",
                "anémopathie",
                "anémophile",
                "anémophilie",
                "anémophobie",
                "anémoscope",
                "anémotrope",
                "anémotropisme",
                "anencéphale",
                "anencéphalie",
                "anencéphalique",
                "anépigraphe",
                "anérection",
                "anergate",
                "anergie",
                "anergique",
                "anergisant",
                "ânerie",
                "anéroïde",
                "ânesse",
                "anesthésiable",
                "anesthésiant",
                "anesthésie",
                "anesthésier",
                "anesthésie-réanimation",
                "anesthésiologie",
                "anesthésiologiste",
                "anesthésique",
                "anesthésiste",
                "anesthésiste-réanimateur",
                "anesthétique",
                "aneth",
                "anéthol",
                "aneuploïde"
        };
        return source;
    }

    public static String[] getEngFrench(){
        String[] source = new String[]{
                "a",
                "abandon",
                "ability (noun)",
                "able (adjective)",
                "about (preposition)",
                "about (adverb)",
                "above (adverb)",
                "above (preposition)",
                "abroad (adverb)",
                "absence (noun)",
                "absent (adjective)",
                "absolute (adjective)",
                "abstract",
                "abstract (adjective)",
                "abuse (verb)",
                "abuse (noun)",
                "abusive",
                "academic (adjective)",
                "accept (verb)",
                "acceptable (adjective)",
                "acceptance",
                "access (verb)",
                "access (noun)",
                "accident (noun)",
                "accompany (verb)",
                "according+to (preposition)",
                "account (verb)",
                "account (noun)",
                "accountant",
                "accurate (adjective)",
                "accuse",
                "ache (noun)",
                "ache",
                "achieve (verb)",
                "acquire (verb)",
                "acquit",
                "across (adverb)",
                "across (preposition)",
                "act (verb)",
                "act (noun)",
                "action (noun)",
                "active",
                "activity (noun)",
                "actor",
                "actress",
                "actual (adjective)",
                "actually (adverb)",
                "adapt (verb)",
                "add (verb)",
                "addition (noun)",
                "additional (adjective)",
                "address (noun)",
                "address (verb)",
                "adequate (adjective)",
                "adjourn",
                "adjust (verb)",
                "administration (noun)",
                "admiration"

        };
        return source;
    }
}
