# tracing-demo
Tracing with Jaeger demo project
# tracing-demo
## Installation

Clone the repository to a local folder

```bash
 git clone https://github.com/jorgebo10/tracing-demo.git .
 cd tracing-demo
 mvn package
 docker build -t tracing-demo .
 ```

 ## Usage
 
```
 docker run -p 8080:8080 tracing-demo
```
