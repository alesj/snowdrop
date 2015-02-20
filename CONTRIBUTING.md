
Snowdrop Contributing Guide
==============================

This document contains information targeted for developers who want to contribute to the Snowdrop project.

### Basic Steps

To contribute to Snowdrop, fork the Snowdrop repository to your own Git, clone your fork, checkout code from the 
`master` branch, commit your work on topic branches, and submit pull requests back to the `master` branch.

If you don't have the GitHub client (`git`), get it from: <http://git-scm.com/>

1. Fork the Snowdrop repository. This creates the project in your own Git. 

2. Clone your forked repository. This creates and populates a directory. For example `snowdrop/` on your local file system with the default remote repository name 'origin'.
   For example:

        git clone https://github.com/YOUR_USER_NAME/snowdrop.git

3. Change to the newly created directory, for example 

        cd snowdrop/

4. Add the remote `upstream` repository so you can fetch any changes to the original forked repository.

        git remote add upstream https://github.com/snowdrop/snowdrop.git

5. Get the latest files from the `upstream` repository.

        git fetch upstream

6. Create a local topic branch to work with your changes or fixes. 

            git checkout -b  <topic-branch-name> upstream/<current-development-branch> 

 * If you are fixing a Bugzilla or JIRA, it is a good practice to use the number in the branch name. For new features try to use a good description for the branch name. 
 * The following are examples of Git checkout commands:

            git checkout -b Bz-98765432 upstream/master
            git checkout -b JDF-9876543 upstream/master
            git checkout -b add-xyz-feature upstream/master

7. Contribute new code or make changes to existing files.

    * The project should be formatted using the JBoss AS profiles found at <http://github.com/jboss/ide-config/tree/master/>
    
     - Code should be well documented with good comments. Please add an author tag (@author) to credit yourself for writing the code.
     - You should use readable variable names to make it easy for users to read the code.

8. Commit your changes to your local topic branch with a commit message that references the JIRA and fully describes the 
fix or change. The preferred grammar is to imagine the commit message starts with "This commit will ...". 

        git commit -m '[SNOWDROP-200] - Fix spelling typo in button X on application Y.'

9. Update your branch with any changes made upstream since you started.
   * Fetch the latest changes from upstream

            git fetch upstream

   * Apply those changes to your branch
   
            git rebase upstream/<current-development-branch>

   * If anyone has committed changes to files that you have also changed, you may see conflicts. 
   Resolve the conflicted files, add them using `git add`, and continue the rebase:
   
            git add <conflicted-file-name>
            git rebase --continue

   * If there were conflicts, it is a good idea to test your changes again to make they still work.
        
10. Push your local topic branch to your GitHub forked repository. This will create a branch on your Git fork repository 
with the same name as your local topic branch name. 

        git push origin HEAD            

   _Note:_ The above command assumes your remote repository is named 'origin'. You can verify your forked remote repository 
   name using the command `git remote -v`.

14. Browse to the <topic-branch-name> branch on your forked Git repository and [open a Pull Request](http://help.github.com/send-pull-requests/). 
Give it a clear title and description.


### License Information and Contributor Agreement

  JBoss Developer Framework is licensed under the Apache License 2.0, as we believe it is one of the most permissive 
  Open Source license. This allows developers to easily make use of the code samples in JBoss Developer Framework. 

  There is no need to sign a contributor agreement to contribute to JBoss Developer Framework. You just need to explicitly 
  license any contribution under the AL 2.0. If you add any new files to JBoss Developer Framework, make sure to add the correct header.

#### Java,  Javascript and CSS files 

      /** 
       * JBoss, Home of Professional Open Source
       * Copyright 2013, Red Hat, Inc. and/or its affiliates, and individual
       * contributors by the @authors tag. See the copyright.txt in the 
       * distribution for a full listing of individual contributors.
       *
       * Licensed under the Apache License, Version 2.0 (the "License");
       * you may not use this file except in compliance with the License.
       * You may obtain a copy of the License at
       * http://www.apache.org/licenses/LICENSE-2.0
       * Unless required by applicable law or agreed to in writing, software
       * distributed under the License is distributed on an "AS IS" BASIS,  
       * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
       * See the License for the specific language governing permissions and
       * limitations under the License.
       */

#### HTML, XML, XSD and XHTML files

      <!--
       JBoss, Home of Professional Open Source
       Copyright 2013, Red Hat, Inc. and/or its affiliates, and individual
       contributors by the @authors tag. See the copyright.txt in the 
       distribution for a full listing of individual contributors.

       Licensed under the Apache License, Version 2.0 (the "License");
       you may not use this file except in compliance with the License.
       You may obtain a copy of the License at
       http://www.apache.org/licenses/LICENSE-2.0
       Unless required by applicable law or agreed to in writing, software
       distributed under the License is distributed on an "AS IS" BASIS,  
       WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
       See the License for the specific language governing permissions and
       limitations under the License.
       -->

#### Properties files and Bash Scripts

       # JBoss, Home of Professional Open Source
       # Copyright 2013, Red Hat, Inc. and/or its affiliates, and individual
       # contributors by the @authors tag. See the copyright.txt in the 
       # distribution for a full listing of individual contributors.
       #
       # Licensed under the Apache License, Version 2.0 (the "License");
       # you may not use this file except in compliance with the License.
       # You may obtain a copy of the License at
       # http://www.apache.org/licenses/LICENSE-2.0
       # Unless required by applicable law or agreed to in writing, software
       # distributed under the License is distributed on an "AS IS" BASIS,  
       # WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
       # See the License for the specific language governing permissions and
       # limitations under the License.

#### SQL files

      --
      -- JBoss, Home of Professional Open Source
      -- Copyright 2013, Red Hat, Inc. and/or its affiliates, and individual
      -- contributors by the @authors tag. See the copyright.txt in the
      -- distribution for a full listing of individual contributors.
      --
      -- Licensed under the Apache License, Version 2.0 (the "License");
      -- you may not use this file except in compliance with the License.
      -- You may obtain a copy of the License at
      -- http://www.apache.org/licenses/LICENSE-2.0
      -- Unless required by applicable law or agreed to in writing, software
      -- distributed under the License is distributed on an "AS IS" BASIS,
      -- WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
      -- See the License for the specific language governing permissions and
      -- limitations under the License.
      --

#### JSP files

      <%--
      JBoss, Home of Professional Open Source
      Copyright 2013, Red Hat, Inc. and/or its affiliates, and individual
      contributors by the @authors tag. See the copyright.txt in the
      distribution for a full listing of individual contributors.

      Licensed under the Apache License, Version 2.0 (the "License");
      you may not use this file except in compliance with the License.
      You may obtain a copy of the License at
      http://www.apache.org/licenses/LICENSE-2.0
      Unless required by applicable law or agreed to in writing, software
      distributed under the License is distributed on an "AS IS" BASIS,
      WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
      See the License for the specific language governing permissions and
      limitations under the License.
      --%>
