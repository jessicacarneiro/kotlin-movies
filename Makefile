run:
	./gradlew bootRun
test:
	SPRING_PROFILES_ACTIVE=test ./gradlew allTests