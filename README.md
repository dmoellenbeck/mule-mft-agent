# mule-mft-agent
Sample mule template works in conjunction with [mule-mft-controller] to faciliate the registration/unregistration, as well as the dynamic push of files from the agent to the controller

### Version
0.2
### Description
This template, after configuration, can be used to :
- Register the agent info to the controller 
- Update the registration in the controller
- Cancel the current registration
- Automatic file upload when copy to a specific and configurable folder


### Configuration
After importing the template into Anypoint Studio and add all the ***.jar** in the **resources** folder into the project **Build Path**, configure the properties file to match your environment. Below is an example of the controller properties file.

### Post-installation
After the agent deployment with the properly configured resource file, run the following command from the localhost to register the agent against the controller:

> curl http://localhost:<agent_port>/register

This command can be executed again **to update** the new agent parameter after changes in the properties file.

To unregister the agent from the controller, run the command:

> curl http://localhost:<agent_port>/unregister

### Properties file sample
```sh
# mft agent setting
# =================

# Agent name to be displayed in the Controller
mft.agent.name=Agent-Name-DEV

# Hostname from the current machine
# (if empty, the ip address will be used)
mft.agent.host=localhost

# HTTP admin port for Registration function. 
# Only accept requests from localhost
mft.agent.http.port=8081

# Internal SFTP server port
mft.agent.sftpd.port=3002

# Internal SFTP server password
mft.agent.sftpd.password=password

# mft agent directory setting
# ===========================
#
# Root Directory used by the agent and server for files exchange
mft.agent.dir.root=/Users/mule/Desktop/mft/agent
#
# Directory (relative to Root Directory) where all files will 
# be automatically upload to the controller
mft.agent.dir.outbox=/outbox
#
# Check file to upload polling frequency (ms)
mft.agent.dir.upload.poll=10000


# mft controller setting
mft.controller.host=localhost
mft.controller.protocol=HTTP
mft.controller.port=8091
mft.controller.basePath=/mft

# mft controller logging if protected by API Manager (Optional)
mft.controller.client_id=
mft.controller.client_secret=
```



   [mule-mft-controller]: <https://github.com/mulesoft-consulting/mule-mft-controller>
   


