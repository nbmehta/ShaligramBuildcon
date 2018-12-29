package hp.bootmgr.constants;

/**
 * This class contains column ids for importing excel files.
 * This constants must match with the column numbers of the excel file to
 * be imported
 */
public class ExcelImport {
    // Employee Import
    public static final int EMPLOYEE_FIRST_NAME = 0;
    public static final int EMPLOYEE_MIDDLE_NAME = 1;
    public static final int EMPLOYEE_LAST_NAME = 2;
    public static final int EMPLOYEE_MOBILE_NO = 3;
    public static final int EMPLOYEE_PHONE_NO = 4;
    public static final int EMPLOYEE_USERNAME = 5;
    public static final int EMPLOYEE_ADDRESS_1 = 6;
    public static final int EMPLOYEE_ADDRESS_2 = 7;
    public static final int EMPLOYEE_CITY = 8;
    public static final int EMPLOYEE_STATE = 9;
    public static final int EMPLOYEE_EMAIL = 10;
    public static final int EMPLOYEE_ACTIVE = 11;

    // end of employee table
    public static final int EMPLOYEE_TABLE_END = 11;

    // Inquiry Import
    public static final int INQUIRY_PROJECT_NAME = 1;
    public static final int INQUIRY_DATE = 2;
    public static final int INQUIRY_IN_TIME = 3;
    public static final int INQUIRY_OUT_TIME = 4;
    public static final int INQUIRY_VISITOR_NAME = 5;
    public static final int INQUIRY_REFERENCED_BY = 6;
    public static final int INQUIRY_ATTENDEE = 7;
    public static final int INQUIRY_AREA_CITY = 8;
    public static final int INQUIRY_VISITOR_NUMBER = 9;
    public static final int INQUIRY_VISITOR_EMAIL = 10;
    public static final int INQUIRY_FOR = 11;
    public static final int INQUIRY_SAMPLE_HOUSE_SHOWN = 12;
    public static final int INQUIRY_INTERESTED = 13;
    public static final int INQUIRY_TYPE = 14;
    public static final int INQUIRY_REMARKS = 15;

    // start of inquiry table
    // ignore index column
    public static final int INQUIRY_TABLE_START = 1;
    // end of inquiry table
    public static final int INQUIRY_TABLE_END = 15;

    // Booking Detail
    public static final int BOOKING_FLAT_NUMBER = 0;
    public static final int BOOKING_DATE = 1;
    public static final int BOOKING_MEMBER_NAME = 2;
    public static final int BOOKING_BOOKED_BY = 3;
    public static final int BOOKING_NOTE = 4;
    public static final int BOOKING_MEMBER_AGE = 5;
    public static final int BOOKING_MEMBER_ADDRESS = 6;
    public static final int BOOKING_MEMBER_OFFICE_ADDRESS = 7;
    public static final int BOOKING_MEMBER_CONTACT_NO1 = 8;
    public static final int BOOKING_MEMBER_CONTACT_NO2 = 9;
    public static final int BOOKING_MEMBER_CONTACT_NO3 = 10;
    public static final int BOOKING_MEMBER_EMAIL = 11;
    public static final int BOOKING_MEMBER_PROFESSION = 12;
    public static final int BOOKING_MEMBER_PAN_NO = 13;
    public static final int BOOKING_MEMBER_DOB = 14;
    public static final int BOOKING_MEMBER_ANNIVERSARY_DATE = 15;

    // Booking Detail Table End
    public static final int BOOKING_DETAIL_TABLE_END = 15;

    // Sheet Numbers
    public static final int SHEET_BOOKING_DETAIL_MEMBER_DETAIL = 0;


}
