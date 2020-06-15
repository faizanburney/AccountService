# This is Generalize script being used by all microservice build scripts
servicesToBuild=$*
if [ "$1" == "all" ]
then
  echo all parmeter is passed so Building All services......
  servicesToBuild=$allServicesNames
fi

for argServices in $servicesToBuild
do  
folder=${microservicesFolders[$argServices]}
  if [ "$folder" == "" ]
  then
    echo "############################### ERROR  #################################################################" 
    echo ERROR:.......Service Name.. $argServices.... is incorrect.......exiting
    echo "############################### ERROR  #################################################################" 
    return
  fi  
echo "################################################################################################"
echo  Building microservice ....$argServices....located at.... ${microservicesFolders[$argServices]}
echo "################################################################################################"
currentDir=$(pwd)
# echo microservice directory.... ${currentDir}
# echo changing directory to ${microservicesFolders[$argServices]}
cd ${microservicesFolders[$argServices]}
echo deleting ...build jars
rm build/libs/*.jar
echo building the microservice
./gradlew build 
echo coming back to microservice directory... ${currentDir}
cd ${currentDir}
echo docker-compose -f $dockerComposeFileName build ${microservicesDockerNames[$argServices]} 
docker-compose -f $dockerComposeFileName build ${microservicesDockerNames[$argServices]} 
done




