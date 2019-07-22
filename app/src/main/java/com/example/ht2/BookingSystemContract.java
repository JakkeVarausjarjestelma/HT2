package com.example.ht2;

import android.provider.BaseColumns;

public class BookingSystemContract {

    private BookingSystemContract() {}

    public static final class ClubEntry implements BaseColumns {
        public static final String TABLE_NAME = "Seura";
        public static final String COLUMN_SEURAID = "SeuraID";
        public static final String COLUMN_NIMI = "Nimi";
    }

    public static final class BookerEntry implements BaseColumns {
        public static final String TABLE_NAME = "Varaaja";
        public static final String COLUMN_VARAAJAID = "VaraajaID";
        public static final String COLUMN_SEURAID = "SeuraID";
    }
    public static final class Entry implements BaseColumns {
        public static final String TABLE_NAME = "Lajivalinta";
        public static final String COLUMN_LAJIID = "LajiID";
        public static final String COLUMN_NIMI = "Nimi";
    }
    public static final class RoomEntry implements BaseColumns {
        public static final String TABLE_NAME = "Sali";
        public static final String COLUMN_SALIID = "SaliID";
        public static final String COLUMN_SEURAID = "LajiID";
        public static final String COLUMN_NIMI = "Nimi";
    }

    public static final class EquipmentEntry implements BaseColumns {
        public static final String TABLE_NAME = "Välineet";
        public static final String COLUMN_VALINEID = "VälineID";
        public static final String COLUMN_SALIID = "SaliID";
        public static final String COLUMN_NIMI = "Nimi";
    }
    public static final class BookingEntry implements BaseColumns {
        public static final String TABLE_NAME = "Varaus";
        public static final String COLUMN_VARAUSID = "VarausID";
        public static final String COLUMN_VARAAJAID = "VaraajaID";
        public static final String COLUMN_SALIID = "SaliID";
        public static final String COLUMN_ALOITUSAIKA = "Aloitusaika";
        public static final String COLUMN_KESTO = "Kesto";
        public static final String COLUMN_PVM = "Päivämäärä";
    }
    public static final class PersonEntry implements BaseColumns {
        public static final String TABLE_NAME = "Henkilo";
        public static final String COLUMN_NIMI = "Nimi";
        public static final String COLUMN_PUHNUM = "Puhelinnumero";
        public static final String COLUMN_VARAAJAID = "VaraajaID";
    }
}
