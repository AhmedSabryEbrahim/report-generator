FROM node:alpine as builder

COPY . /app
WORKDIR /app
RUN npm install
RUN npm run build --prod

FROM nginx:alpine
EXPOSE 80
COPY --from=builder /app/dist/report-frontend /usr/share/nginx/html
