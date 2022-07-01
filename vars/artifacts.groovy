def prepare() {

  if (env.APPTYPE == "maven" ) {
    sh """
      mvn package 
      mv target/${COMPONENT}-1.0.jar ${COMPONENT}.jar
      zip -r ${COMPONENT}-${TAG_NAME}.zip ${COMPONENT}.jar
    """
  }

  if (env.APPTYPE == "nodejs" ) {
    sh """
      npm install
      zip -r ${COMPONENT}-${TAG_NAME}.zip server.js node_modules
    """
  }

  if (env.APPTYPE == "python" ) {
    sh """
      zip -r ${COMPONENT}-${TAG_NAME}.zip *.py *.ini requirements.txt
    """
  }
  else if (env.SERVICE_TYPE == "nginx") {
    sh """
      cd static
      zip -r ../${COMPONENT}-${TAG_NAME}.zip *
    """
  }

}

def publish() {
  sh """
    curl -f -v -u admin:admin123 --upload-file ${COMPONENT}-${TAG_NAME}.zip http://172.31.12.154:8081/repository/${COMPONENT}/${COMPONENT}-${TAG_NAME}.zip
  """
}