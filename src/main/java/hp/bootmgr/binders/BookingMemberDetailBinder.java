package hp.bootmgr.binders;

import hp.bootmgr.vo.BookingDetail;
import hp.bootmgr.vo.Employee;

public class BookingMemberDetailBinder {
    private BookingDetail bookingDetail;
    private Employee employee;

    public BookingDetail getBookingDetail() {
        return bookingDetail;
    }

    public void setBookingDetail(BookingDetail bookingDetail) {
        this.bookingDetail = bookingDetail;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
