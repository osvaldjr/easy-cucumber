FROM node:6.11-slim
RUN npm install -g stubby
ENTRYPOINT ["sh","-c","stubby -s 9001 -t 9002 -a 9003 -w"]