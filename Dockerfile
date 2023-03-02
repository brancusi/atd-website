# Use a Node.js base image
FROM node:14-alpine

LABEL org.atd.authors="Aram Zadikian"

# Set the working directory to /app
WORKDIR /app

# Copy the package.json and package-lock.json files to the container
COPY package*.json ./

# Install the project dependencies
RUN npm install

# Copy the rest of the project files to the container
COPY . .

# Build the project for release
RUN npm run release

RUN npm install http-server

# Expose port 3000 for the server
EXPOSE 8080

# Start the server
CMD ["http-server", "public"]