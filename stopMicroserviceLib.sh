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
echo  Stopping microservice ....$argServices....located at.... ${microservicesFolders[$argServices]}
echo "################################################################################################"
currentDir=$(pwd)
echo docker-compose -f $dockerComposeFileName stop ${microservicesDockerNames[$argServices]} &
docker-compose -f $dockerComposeFileName stop ${microservicesDockerNames[$argServices]} &
done




