# Extends the flurdy's image which already has Oracle JDK 7
FROM flurdy/oracle-java7

# Sets the maintainer field
LABEL maintainer="StanleySathler"

# Working directory
WORKDIR /root

# Move current files into the container's /root dir
ADD . /root

# Install Scala
RUN apt-get update
RUN apt-get install -y scala

# Install SBT
RUN echo "deb https://dl.bintray.com/sbt/debian /" | tee -a /etc/apt/sources.list.d/sbt.list
RUN apt-key adv --keyserver hkp://keyserver.ubuntu.com:80 --recv 642AC823
RUN apt-get update
RUN apt-get install -y sbt

# Run initial command (whenever the container starts)
CMD ["sbt", "\"~;container:start;\""]
