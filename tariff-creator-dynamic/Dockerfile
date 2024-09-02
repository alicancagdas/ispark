# Step 1: Use Node.js as the base image
FROM node:18 AS build

# Step 2: Set the working directory in the container
WORKDIR /app

# Step 3: Copy package.json and package-lock.json to the working directory
COPY package*.json ./

# Step 4: Install dependencies
RUN npm install

# Step 5: Copy the rest of your application files to the container
COPY . .

# Step 6: Set Node.js environment variable for OpenSSL
ENV NODE_OPTIONS=--openssl-legacy-provider

# Step 7: Build the application
RUN npm run build

# Step 8: Use an Nginx image to serve the React application
FROM nginx:stable-alpine

# Step 9: Copy the build files to the Nginx html directory
COPY --from=build /app/build /usr/share/nginx/html

# Step 10: Expose port 80
EXPOSE 80

# Step 11: Start Nginx server
CMD ["nginx", "-g", "daemon off;"]
