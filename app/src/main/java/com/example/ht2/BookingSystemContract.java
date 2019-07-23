package com.example.ht2;

import android.provider.BaseColumns;

public class BookingSystemContract {

    private BookingSystemContract() {}

    public static final class ClubEntry implements BaseColumns {
        public static final String TABLE_NAME = "Seura";
        public static final String COLUMN_CLUBID = "SeuraID";
        public static final String COLUMN_NAME = "Nimi";
    }

    public static final class BookerEntry implements BaseColumns {
        public static final String TABLE_NAME = "Varaaja";
        public static final String COLUMN_BOOKERID = "VaraajaID";
        public static final String COLUMN_CLUBID = "SeuraID";
    }
    public static final class SportEntry implements BaseColumns {
        public static final String TABLE_NAME = "Lajivalinta";
        public static final String COLUMN_SPORTID = "LajiID";
        public static final String COLUMN_NAME = "Nimi";
    }
    public static final class RoomEntry implements BaseColumns {
        public static final String TABLE_NAME = "Sali";
        public static final String COLUMN_ROOMID = "SaliID";
        public static final String COLUMN_SPORTID = "LajiID";
        public static final String COLUMN_NAME = "Nimi";
    }

    public static final class EquipmentEntry implements BaseColumns {
        public static final String TABLE_NAME = "Välineet";
        public static final String COLUMN_EQUIPMENTID = "VälineID";
        public static final String COLUMN_ROOMID = "SaliID";
        public static final String COLUMN_NAME = "Nimi";
    }
    public static final class BookingEntry implements BaseColumns {
        public static final String TABLE_NAME = "Varaus";
        public static final String COLUMN_BOOKINGID = "VarausID";
        public static final String COLUMN_BOOKERID = "VaraajaID";
        public static final String COLUMN_ROOMID = "SaliID";
        public static final String COLUMN_STARTTIME = "Aloitusaika";
        public static final String COLUMN_ENDTIME = "Lopetusaika";
        public static final String COLUMN_DATE = "Päivämäärä";
    }
    public static final class PersonEntry implements BaseColumns {
        public static final String TABLE_NAME = "Henkilö";
        public static final String COLUMN_NAME = "Nimi";
        public static final String COLUMN_PHONENUMBER = "Puhelinnumero";
        public static final String COLUMN_BOOKERID = "VaraajaID";
    }
}
