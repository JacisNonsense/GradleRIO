package edu.wpi.first.gradlerio.frc

import edu.wpi.first.gradlerio.wpi.WPIExtension
import groovy.transform.CompileStatic
import jaci.gradle.deploy.context.DeployContext
import jaci.gradle.deploy.target.location.SshDeployLocation
import org.apache.log4j.Logger
import org.gradle.api.Project

import javax.inject.Inject
import java.util.regex.Matcher
import java.util.regex.Pattern

@CompileStatic
class VMXPi extends FRCCompatibleTarget {

    private Logger log;

    @Inject
    VMXPi(String name, Project project) {
        super(name, project)
        log = Logger.getLogger(this.toString())

        // TODO(Kauai): Change this in your image!
        this.directory = '/home/lvuser'

        this.maxChannels = 4
    }

    int team
    void setTeam(int team) {
        this.team = team
        setAddresses(
            "172.22.11.2"                                          // USB
        )
    }

    void setAddresses(String... addresses) {
        this.locations.clear()
        addresses.each { String addr ->
            this.addAddress(addr)
        }
    }

    void addAddress(String address) {
        this.getLocations().location(SshDeployLocation) { SshDeployLocation loc ->
            loc.setAddress(address)
            loc.setIpv6(false)
            loc.setUser("pi")
            loc.setPassword("raspberry")
        }
    }

    @Override
    String toString() {
        return "VMXPi[${name}]".toString()
    }
}
