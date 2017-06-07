package Common;

/**
 * Created by Brandon on 24/05/2017.
 */
public class CommonVoid {

    public static enum TypeErreurServeur
    {
        ERR0(200, "OK"),
        ERR1(404, "file not found"),
        ERR2(502, "bad gateway"),
        ERR3(3, "Disque plein");

        public final String libelle;
        public final int code;

        TypeErreurServeur(int p_code, String p_libelle)
        {
            this.libelle = p_libelle;
            this.code = p_code;
        }

        public static TypeErreurServeur getStringFromValue(int valeur) {
            switch (valeur) {
                case 0 :
                    return ERR0;
                case 1 :
                    return ERR1;
                case 2 :
                    return ERR2;
                case 3 :
                    return ERR3;
                default :
                    return ERR0;
            }
        }
    }

}
