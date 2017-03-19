### Add archtype in SpringMVC
http://stackoverflow.com/questions/31721013/adding-org-glassfish-jersey-archetypes-in-eclipse
  1. Open Window > Preferences
  2. Open Maven > Archetypes
  3. Click Add Remote Catalog and add the following:
      Catalog File: http://repo1.maven.org/maven2/archetype-catalog.xml
      Description: maven catalog
  4. Restart eclipse

### If you donot have Dynamic Web Project present in Eclipse or You donot have the option to Run on Server after you right click "Run as" then,
http://stackoverflow.com/questions/5531402/newbie-in-eclipse-i-dont-have-dynamic-web-project-i-am-under-linux-ubuntu
  1. help>Install new software
  2. Based on your eclipse choose "http://download.eclipse.org/releases/juno" and paste in "work with" input box and click add
  3. give any name you want - plugin
  4. In the list select>"Web, XML, Java EE and OSGi Enterprise Development">Eclipse Java EE Developer Tools. select and install it.
  5. After restart you will have your Dyanmic web project option.
