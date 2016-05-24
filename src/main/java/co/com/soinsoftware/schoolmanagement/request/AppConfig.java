package co.com.soinsoftware.schoolmanagement.request;

import org.glassfish.jersey.server.ResourceConfig;

public class AppConfig extends ResourceConfig {
	
	public AppConfig() {
		this.register(ClassRequestHandler.class);
		this.register(ClassRoomRequestHandler.class);
		this.register(GradeRequestHandler.class);
		this.register(PeriodRequestHandler.class);
		this.register(ReportRequestHandler.class);
		this.register(SchoolRequestHandler.class);
		this.register(SubjectRequestHandler.class);
		this.register(TimeRequestHandler.class);
		this.register(UserRequestHandler.class);
		this.register(UserTypeRequestHandler.class);
		this.register(YearRequestHandler.class);
    }
}