run:
	SPRING_PROFILES_ACTIVE=local ./gradlew bootRun
test:
	SPRING_PROFILES_ACTIVE=test ./gradlew allTests