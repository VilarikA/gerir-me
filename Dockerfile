# Extends the flurdy's image which already has Oracle JDK 7
FROM flurdy/oracle-java7

# Sets the maintainer field
LABEL maintainer="StanleySathler"

# Working directory
WORKDIR /app

# Run initial command (whenever the container starts)
CMD ["sbt", "\"~;container:start;\""]
